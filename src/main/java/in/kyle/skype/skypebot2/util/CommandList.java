package in.kyle.skype.skypebot2.util;

import in.kyle.skype.skypebot2.commands.all.CAdmin;
import in.kyle.skype.skypebot2.commands.all.CBase64;
import in.kyle.skype.skypebot2.commands.all.CBinary;
import in.kyle.skype.skypebot2.commands.all.CCalc;
import in.kyle.skype.skypebot2.commands.all.CCoin;
import in.kyle.skype.skypebot2.commands.all.CDefine;
import in.kyle.skype.skypebot2.commands.all.CEmojis;
import in.kyle.skype.skypebot2.commands.all.CFind;
import in.kyle.skype.skypebot2.commands.all.CGoogle;
import in.kyle.skype.skypebot2.commands.all.CHelp;
import in.kyle.skype.skypebot2.commands.all.CID;
import in.kyle.skype.skypebot2.commands.all.CInfo;
import in.kyle.skype.skypebot2.commands.all.CInsult;
import in.kyle.skype.skypebot2.commands.all.CList;
import in.kyle.skype.skypebot2.commands.all.CMC;
import in.kyle.skype.skypebot2.commands.all.CPing;
import in.kyle.skype.skypebot2.commands.all.CPoll;
import in.kyle.skype.skypebot2.commands.all.CRandomVideo;
import in.kyle.skype.skypebot2.commands.all.CSay;
import in.kyle.skype.skypebot2.commands.all.CUrl;
import in.kyle.skype.skypebot2.commands.all.CWeather;
import in.kyle.skype.skypebot2.commands.all.CWhois;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle on 12/15/2015.
 */
public class CommandList {
    public static final List<Object> COMMANDS = new ArrayList<Object>() {
        {
            add(new CAdmin());
            add(new CBase64());
            add(new CBinary());
            add(new CCalc());
            add(new CCoin());
            add(new CDefine());
            add(new CEmojis());
            add(new CFind());
            add(new CGoogle());
            add(new CHelp());
            add(new CID());
            add(new CInfo());
            add(new CInsult());
            add(new CList());
            add(new CMC());
            add(new CPing());
            add(new CPoll());
            add(new CRandomVideo());
            add(new CSay());
            add(new CUrl());
            add(new CWeather());
            add(new CWhois());
        }
    };
}
