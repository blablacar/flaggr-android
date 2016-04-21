# Flaggr

A library that allows you to enable/disable features and/or part of code regarding a Context

## How to include it

```groovy
compile 'com.comuto:flaggr:0.1'
```

## How to use it 

Initialize at the start of the application :

```java
// on onCreate of Application :
Flaggr flaggr = Flaggr.with(this);
flaggr.loadConfig(BuildConfig.FEATURE_FLAG_URL);
```

To reload the feature flags file, the current best solution is to add this code in the onResume method of your MainActivity
```java
((MyApplication) getApplication()).getFlaggr().reloadConfig();
```

Checking if a flag is active :

1 - get Flaggr instance :

  * In an activity : 
```java
((MyApplication) getApplication()).getFlaggr();
```
  
  * In a Fragment : 
```java
((MyApplication) getActivity().getApplication()).getFlaggr();
```

2 - Construct the flag context and pass it to isActive (There is a default value of each setter, you can override only the needed setters) : 
```java
MyFlagContext.FlagContextBuilder builder = new MyFlagContext.FlagContextBuilder()
        .setLocale(Locale.getDefault())
        .setUserId(myUserId);
flaggr.isActive(getString(R.string.my_flag), builder.build());
```
Your context can contain any type of data, but IT MUST IMPLEMENTS THE INTERFACE FlagContextInterface

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




