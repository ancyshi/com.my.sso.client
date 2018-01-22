package com.my.factory;


/**
 * 具体工厂
 * 
 * @author by_ww
 *
 */
public class SessionFactory implements AbstractFactory {

	@Override
	public AbstractSession generateSession() {
		return new LocalSession();
	}

}
