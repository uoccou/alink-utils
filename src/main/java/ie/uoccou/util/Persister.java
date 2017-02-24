package ie.uoccou.util;

import java.io.Serializable;

public interface Persister<Obj extends Serializable> {

	public void persist(Obj ob);

	public Obj restore();
}
