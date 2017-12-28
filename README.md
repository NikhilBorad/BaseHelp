# BaseHelp
Some base features for android app. Boost up your developement.

Happy coding.

# Pre-requisites

For using this library you need to include Google's Maven repository for latest Support libraries.

You can do that as give below :

In your project's build.gradle file include as :

```
allprojects {
    repositories {
        jcenter()
        maven {
            url "https://jitpack.io" 
        }
    }
}
```
In your app's build.gradle file include as :

```
dependencies{
    //Other dependecies...
    compile 'com.github.nikhilborad:basehelp:1.02'
}
```

# Usage

Now, any of your activity extends AppCompactActivity. So basically you need to do is replace AppCompactActivity with BaseAppCompactActivity to your class
```
public class MainActivity extends BaseAppCompactActivity{

    //...
    nbSetUpActionBar("HOME");
    ...//
    
}
```
After successfully done, type nb and hit ctrl+space and all available methods from this library will show up.

To be more precise I put "nb" prefix to all methods name.
