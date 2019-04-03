package spring.road.beans.support;

import com.sun.corba.se.impl.io.TypeMismatchException;

public interface TypeConverter {


	<T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;


}
