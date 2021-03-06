
all: test

clean:
	rm -rf target
	(cd java && mvn clean)

TESTFILES = \
        target/results/base/integers_test.result \
	target/results/base/lists_test.result \
	target/results/base/maps_test.result \
	target/results/base/equals_test.result \
	target/results/base/visitor_test.result \

test: $(TESTFILES)

COPY=mkdir -p `echo $@ | sed 's/\/[^/]*$$//' ` && cp -f $< $@

target/languish/base/integers.lish: languish/base/integers.lish \
		target/languish/parsers/lambda_plus/parser.lish
	$(COPY)

target/languish/base/integers_test.lish: languish/base/integers_test.lish \
		target/languish/base/integers.lish \
		target/languish/base/testing/test_runner.lish \
		target/languish/parsers/lambda_plus/parser.lish
	$(COPY)

target/languish/base/maps.lish: languish/base/maps.lish \
		target/languish/parsers/lambda_plus/parser.lish
	$(COPY)

target/languish/base/maps_test.lish: languish/base/maps_test.lish \
		target/languish/base/maps.lish \
		target/languish/base/testing/test_runner.lish \
		target/languish/parsers/lambda_plus/parser.lish
	$(COPY)

target/languish/base/equals.lish: languish/base/equals.lish \
		target/languish/base/bool.lish \
		target/languish/parsers/lambda_plus/parser.lish
	$(COPY)

target/languish/base/equals_test.lish: languish/base/equals_test.lish \
		target/languish/base/equals.lish \
		target/languish/base/testing/test_runner.lish \
		target/languish/parsers/lambda_plus/parser.lish
	$(COPY)

target/languish/base/bool.lish: languish/base/bool.lish \
		target/languish/parsers/lambda_plus/parser.lish
	$(COPY)

target/languish/base/lists.lish: languish/base/lists.lish \
		target/languish/parsers/lambda_plus/parser.lish
	$(COPY)

target/languish/base/lists_test.lish: languish/base/lists_test.lish \
		target/languish/base/lists.lish \
		target/languish/base/testing/test_runner.lish \
		target/languish/parsers/lambda_plus/parser.lish
	$(COPY)

target/languish/base/visitor.lish: languish/base/lists.lish \
		target/languish/parsers/lambda_plus/parser.lish \
		target/languish/base/lists.lish \
		target/languish/base/maps.lish
	$(COPY)

target/languish/base/visitor_test.lish: languish/base/visitor_test.lish \
		target/languish/base/visitor.lish \
		target/languish/base/testing/test_runner.lish \
		target/languish/parsers/lambda_plus/parser.lish
	$(COPY)

target/languish/base/testing/test_runner.lish: languish/base/testing/test_runner.lish \
		target/languish/parsers/lambda_plus/parser.lish
	$(COPY)

target/languish/parsers/lambda_plus/parser.lish: languish/parsers/lambda_plus/parser.lish
	$(COPY)


target/results/%_test.result: target/languish/%_test.lish target/lish 
	mkdir -p `echo $@ | sed 's/\/[^/]*$$//' `
	target/lish --lp languish $*_test | tee $@ || rm -f $@

# HACK HACK HACK
target/lish:
	(cd java && mvn install)
	mkdir -p target
	cp `(cd java/languish-exec && mvn package|grep "Building jar"| cut -b 22-)` target/lish.jar
	rm -f target/lish
	echo "java -jar $(PWD)/target/lish.jar \$$@" > target/lish
	chmod +x target/lish