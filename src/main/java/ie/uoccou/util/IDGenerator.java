package ie.uoccou.util;

import java.io.Serializable;

public interface IDGenerator extends Serializable{

	public String getId(String basename);
}
