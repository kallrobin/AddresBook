package com.gmail.kallrobin92.addressBookFinal;

import java.io.IOException;

/**
 * Created by Robin Gk on 2016-12-06 as a school project.
 * email kallrobin92@gmail.com
 */
public class Main {
    public static void main(String[] args) throws IOException {
        new LoggerConfig();
        Application application = new Application();
        application.run();
    }
}
