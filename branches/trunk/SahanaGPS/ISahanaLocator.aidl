// My AIDL file, named SomeClass.aidl
// Note that standard comment syntax is respected.
// Comments before the import or package statements are not bubbled up
// to the generated interface, but comments above interface/method/field
// declarations are added to the generated interface.

// Include your fully-qualified package statement.
package cmu.mobilelab;

// See the list above for which classes need
// import statements (hint--most of them)
import cmu.mobilelab.SahanaLocatorService;
import cmu.mobilelab.SahanaLocator; 

// Declare the interface.
interface ISahanaLocator {
    
    // Methods can take 0 or more parameters, and
    // return a value or void.
    SahanaLocation getCurrentBestLocation(); 
}