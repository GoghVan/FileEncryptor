package com.david.action;

import org.junit.Test;

/**
 * Created by Gavin on 2019/4/23.
 */
public class FileActionTest {
    @Test
    public void execute() throws Exception {
        FileEncryptorAction fileAction = new FileEncryptorAction();
        fileAction.execute();
    }

}