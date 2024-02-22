package com.yusufsezer.util;

public class ViewUtils {

    private final static String PROJECT_NAME = "Java Servlet Contact";

    protected static String header(String title) {
        return """
               <head>
                 <meta charset="UTF-8">
                 <title>%s - %s</title>
                 <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
               </head>

               """.formatted(title, PROJECT_NAME);
    }

    public static String buildPage(String pageTitle, String pageContent) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("""
                             <!DOCTYPE html>
                             <html lang="en">

                             """);

        stringBuilder.append(header(pageTitle));

        stringBuilder.append("""

                             <body>
                              <main role="main" class="container pb-3">
                                 <h1>%s</h1>
                                 <h4>%s</h4>
                                 <hr>
                             
                             """.formatted(pageTitle, PROJECT_NAME));

        stringBuilder.append(pageContent);

        stringBuilder.append("""

                            </main>
                            </body>

                            </html>""");

        return stringBuilder.toString();
    }

}
