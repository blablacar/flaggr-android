# Flaggr

A library that allows you to enable/disable features and/or part of code regarding a Context

## How to include it

```groovy
compile 'com.comuto:flaggr:0.3'
```

## How to use it 

Initialize at the start of the application :

```java
// on onCreate of Application :
Flaggr.with(this).loadConfig(FLAGGR_CONFIG_URI);
```

The "FLAGGR_CONFIG_URI" is the uri where your flaggs are stored (json file for example)

To reload the feature flags file, the current best solution is to add this code in the onResume method of your MainActivity
```java
Flaggr.with(this).reloadConfig();
```

Checking if a flag is active :

1 - get Flaggr instance :

  * In an activity & Fragment :
```java
Flaggr.with(this);
```

2 - Construct the flag context and pass it to isActive (There is a default value of each setter, you can override only the needed setters) : 
```java
MyFlagContext.FlagContextBuilder builder = new MyFlagContext.FlagContextBuilder()
        .setLocale(Locale.getDefault())
        .setUserId(myUserId);
Flaggr.with(this).isActive(getString(R.string.my_flag), builder.build());
```
Your context can contain any type of data, but IT MUST IMPLEMENTS THE INTERFACE FlagContextInterface

You can also specify a default value if the flag is not found or an error happens when processing
```java
Flaggr.with(this).isActive(getString(R.string.my_flag), builder.build(), true);
```

## License
```
Copyright 2016 BlaBlaCar, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```




