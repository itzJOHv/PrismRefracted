package network.darkhelmet.prism.parameters;

import network.darkhelmet.prism.Prism;
import network.darkhelmet.prism.actionlibs.QueryParameters;
import network.darkhelmet.prism.api.actions.PrismProcessType;
import network.darkhelmet.prism.utils.DateUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.regex.Pattern;

public class SinceParameter extends SimplePrismParameterHandler {
    /**
     * Time since parameter.
     */
    public SinceParameter() {
        super("Since", Pattern.compile("[\\w]+"), "t", "since");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void process(QueryParameters query, String alias, String input, CommandSender sender) {
        if (input.equalsIgnoreCase("none")) {
            query.setIgnoreTime(true);
        } else {
            final Long date = DateUtil.translateTimeStringToDate(input);

            if (date != null) {
                query.setSinceTime(date);
            } else {
                throw new IllegalArgumentException(
                        "Date/time for 'since' parameter value not recognized. Try /pr ? for help");
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void defaultTo(QueryParameters query, CommandSender sender) {
        if (query.getProcessType().equals(PrismProcessType.DELETE)) {
            return;
        }
        if (!query.getFoundArgs().contains("before") && !query.getFoundArgs().contains("since")) {
            final FileConfiguration config = Bukkit.getPluginManager().getPlugin("Prism").getConfig();

            Long date = DateUtil.translateTimeStringToDate(config.getString("prism.queries.default-time-since"));

            if (date <= 0L) {
                Prism.log("Error - date range configuration for prism.time-since is not valid");
                date = DateUtil.translateTimeStringToDate("3d");
            }

            query.setSinceTime(date);
            query.addDefaultUsed("t:" + config.getString("prism.queries.default-time-since"));
        }
    }
}