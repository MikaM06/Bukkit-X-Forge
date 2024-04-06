package fr.plb.forge;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        // Register the command
        this.getCommand("test").setExecutor(this);
    }

    public class MyCustomPacket {
        private final String playerName;
        private final String command;

        public MyCustomPacket(String playerName, String command) {
            this.playerName = playerName;
            this.command = command;
        }

        public String getPlayerName() {
            return playerName;
        }

        public String getCommand() {
            return command;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            // Create a custom packet and send it to the mod
            // You'll need to implement this yourself
            MyCustomPacket packet = new MyCustomPacket(player.getName(), "/gui");
            try {
                sendPacketToMod(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    // Implement the method to send the packet to the mod
    private void sendPacketToMod(MyCustomPacket packet) throws IOException {
        Socket socket = new Socket("127.0.0.1", 62630);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(packet);
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
