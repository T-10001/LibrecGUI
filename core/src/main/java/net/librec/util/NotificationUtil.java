package net.librec.util;
import java.awt.Image;
import java.awt.event.ActionListener;

import ds.desktop.notify.DesktopNotify;
import ds.desktop.notify.NotificationBuilder;
import ds.desktop.notify.NotifyTheme;

public class NotificationUtil extends NotificationBuilder {

	
	
	@Override
	public DesktopNotify build() {
		return super.build();
	}

	@Override
	public DesktopNotify buildAndReset() {
		return super.buildAndReset();
	}

	@Override
	public void reset() {
		super.reset();
	}

	@Override
	public NotificationBuilder setAction(ActionListener action) {
		return super.setAction(action);
	}

	@Override
	public NotificationBuilder setIcon(Image icon) {
		return super.setIcon(icon);
	}

	@Override
	public NotificationBuilder setMessage(String message) {
		return super.setMessage(message);
	}

	@Override
	public NotificationBuilder setTheme(NotifyTheme theme) {
		return super.setTheme(theme);
	}

	@Override
	public NotificationBuilder setTimeOut(long timeOut) {
		return super.setTimeOut(timeOut);
	}

	@Override
	public NotificationBuilder setTitle(String title) {
		return super.setTitle(title);
	}

	@Override
	public NotificationBuilder setType(int type) {
		return super.setType(type);
	}

	
	
	public NotificationUtil() {
		super();
	}
	
	public NotificationUtil(String message, boolean timeout) {
		super();
		makeNotification(message, timeout);
	}

	public void notify(String message, boolean timeout)
    {
		makeNotification(message, timeout);
    }
	
	public static void staticNotify(String message, boolean timeout)
    {
		NotificationBuilder builder = new NotificationBuilder();
		builder.setMessage(message);
		DesktopNotify notification = builder.build();
		notification.show();
		if(timeout)
			notification.setTimeout(2500);
    }
	
	private void makeNotification(String message, boolean timeout)
	{
		NotificationBuilder builder = new NotificationBuilder();
		builder.setMessage(message);
		DesktopNotify notification = builder.build();
		notification.show();
		if(timeout)
			notification.setTimeout(2500);
	}
	
	
}
