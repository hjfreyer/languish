package languish.lambda;

public interface Operation {
  public Term reduce(Term term);

  public boolean isReduced(Term term);

  public Term replaceAllReferencesToParam(Term term, int id, Term with);
}
