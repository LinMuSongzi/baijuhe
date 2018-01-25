// ICalculateInterface.aidl
package com.l.ipc;

// Declare any non-default types here with import statements


interface ICalculateInterface {

    double adding(double one,double two);

    double multiply(double one,double two);

    double subtract(double one,double two);

    double divide(double one,double two);

    String getServiceName();

}
