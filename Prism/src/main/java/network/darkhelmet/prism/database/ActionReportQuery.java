package network.darkhelmet.prism.database;

import org.bukkit.command.CommandSender;

public interface ActionReportQuery extends SelectQuery {
    void report(CommandSender sender);
}
