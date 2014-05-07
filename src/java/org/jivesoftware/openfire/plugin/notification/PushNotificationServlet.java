package org.jivesoftware.openfire.plugin.notification;

import org.jivesoftware.admin.AuthCheckFilter;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.plugin.NotificationPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by zhoulq on 5/6/14.
 */
public class PushNotificationServlet extends HttpServlet {

    private static final Logger Log = LoggerFactory.getLogger(PushNotificationServlet.class);

    private static final String SERVICE_NAME = "notification/pushnotification";
    private NotificationPlugin plugin;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        plugin = (NotificationPlugin) XMPPServer.getInstance().getPluginManager().getPlugin("notification");
        AuthCheckFilter.addExclude(SERVICE_NAME);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        System.out.println("1--PushNotificationServlet doGet()");
        String title = req.getParameter("title");
        String message = req.getParameter("message");
        plugin.sendNotificationToAllUser(title, message, "");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();
        System.out.println("PushNotificationServlet doPost()");
        out.print("PushNotificationServlet doPost()");
        Log.info("PushNotificationServlet doPost()");
    }

    @Override
    public void destroy() {
        super.destroy();
        AuthCheckFilter.removeExclude(SERVICE_NAME);
    }
}
