package com.io.payload;

import com.io.utilities.Utilities;

public class PayloadAPI {

	public static String getLaunchSiteAPI() {

        String value = "{\r\n"
        		+ "  cores {\r\n"
        		+ "    missions {\r\n"
        		+ "      flight\r\n"
        		+ "      name\r\n"
        		+ "    }\r\n"
        		+ "  }\r\n"
        		+ "}";

        String payload = Utilities.graphqlToJsonString(value);
        return payload;
    }
}

