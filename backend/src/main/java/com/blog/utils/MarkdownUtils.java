package com.blog.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class MarkdownUtils { 

    public static String generateDefaultReadme(String name, String username, Instant createdAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
                .withZone(ZoneId.systemDefault());
        String joinedDate = formatter.format(createdAt);

        return """
                # 👋 Hello, I'm %s

                Welcome to my profile!  
                I'm excited to be part of this community.

                ## 🧑‍💻 About Me
                - **Full name:** %s  
                - **Username:** `%s`  
                - **Joined:** %s  

                ---

                _Thanks for visiting my profile!_  

                """.formatted(name, name, username, joinedDate);
    }
}
