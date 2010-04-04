
all:
	echo "nothing to see here"

clean:
	rm -rf target
	(cd java && mvn clean)

TESTFILES = target/results/rocket/foo_test.result
test: $(TESTFILES)

COPY=mkdir -p `echo $@ | sed 's/\/[^/]*$$//' ` && cp $< $@

target/languish/base/bar.lish: languish/bar.lish
	$(COPY)

target/languish/rocket/foo_test.lish: languish/rocket/foo_test.lish target/languish/bar.lish
	$(COPY)


target/results/%_test.result: target/languish/%_test.lish target/lish 
	mkdir -p `echo $@ | sed 's/\/[^/]*$$//' `
	target/lish --lp languish $*_test > $@ || rm -f $@

# HACK HACK HACK
target/lish:
	(cd java && mvn install)
	mkdir -p target
	cp `(cd java/languish-exec && mvn package|grep "Building jar"| cut -b 22-)` target/lish.jar
	rm -f target/lish
	echo "java -jar $(PWD)/target/lish.jar \$$@" > target/lish
	chmod +x target/lish