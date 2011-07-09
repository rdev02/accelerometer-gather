package com.rdev.accelgatherer.provider;

/**
 * Created by IntelliJ IDEA.
 * User: revenge
 * Date: 7/9/11
 * Time: 11:53 PM
 */
public class AccelProviderFactory {

    private AccelProviderFactory() {
    }

    public static AccelProviderFactory getInstance(){return SingleToneHolder.INSTANCE;}


    private static class SingleToneHolder{
        protected  SingleToneHolder(){}
        private static AccelProviderFactory INSTANCE = new AccelProviderFactory();
    }
}
