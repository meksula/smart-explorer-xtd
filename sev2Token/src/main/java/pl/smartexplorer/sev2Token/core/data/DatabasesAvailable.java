package pl.smartexplorer.sev2Token.core.data;

/**
 * @author
 * Karol Meksuła
 * 14-10-2018
 * */

public enum DatabasesAvailable {
    MYSQL {
        @Override
        public String getDatabaseDriver() {
            return "com.mysql.jdbc.Driver";
        }

        @Override
        public String getTableCreationQuery(String tableName) {
            return "CREATE TABLE " + tableName + " (userId VARCHAR(255) NOT NULL, " +
                    "username VARCHAR(255), sev2TokenType VARCHAR (255), uuid VARCHAR (255), issue_date VARCHAR(255), " +
                    "ip_address VARCHAR(255), isExpired VARCHAR(5), PRIMARY KEY(userId));";
        }
    },
    POSTGRESQL {
        @Override
        public String getDatabaseDriver() {
            return "org.postgresql.Driver";
        }

        @Override
        public String getTableCreationQuery(String tableName) {
            return "CREATE TABLE " + tableName + " (userId VARCHAR(255) NOT NULL, " +
                    "username VARCHAR(255), sev2TokenType VARCHAR (255), uuid VARCHAR (255), issue_date VARCHAR(255), " +
                    "ip_address VARCHAR(255), isExpired VARCHAR(5), PRIMARY KEY(userId));";
        }
    };

    public abstract String getDatabaseDriver();

    public abstract String getTableCreationQuery(final String tableName);
}
