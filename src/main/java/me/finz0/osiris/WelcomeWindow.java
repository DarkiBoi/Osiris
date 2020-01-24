package me.finz0.osiris;

import net.minecraft.client.Minecraft;

import javax.swing.*;

public class WelcomeWindow extends JFrame {
	public WelcomeWindow() {
		this.setTitle("osiris");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		String message = "Osiris initialized.\nWelcome " + Minecraft.getMinecraft().getSession().getUsername();
		JOptionPane.showMessageDialog(this, message, "Osiris", JOptionPane.PLAIN_MESSAGE);
	}
}	
		