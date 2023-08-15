package com.krubo.apmplugin.data;

import java.util.ArrayList;
import java.util.List;

public class Config {
    public List<String> timecost_pkg = new ArrayList<>();//多个包路径，以分号隔开 xxx.xxx.xxx;xx.xx.xx
    public List<String> timecost_methodClass = new ArrayList<>();//多个类名和方法名，以分号隔开，xxx.xxx.xxx.classname/methodName;xx.xx.classname/methodName
    public List<String> timecost_blackClass = new ArrayList<>();//多个类名，以分号隔开 xxx.xxx.xxx.classname;xx.xx.xx.classname
    public List<String> timecost_blackMethodClass = new ArrayList<>();//多个类名和方法名，以分号隔开，xxx.xxx.xxx.classname/methodName;xx.xx.classname/methodName
}
