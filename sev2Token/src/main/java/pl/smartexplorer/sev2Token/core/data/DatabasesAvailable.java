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
        public String getTableCreationQuery() {
            return "CREATE TABLE sev2token (id INTEGER NOT NULL AUTO_INCREMENT, userId VARCHAR(255), " +
                    "username VARCHAR(255), sev2TokenType VARCHAR (255), uuid VARCHAR (255), PRIMARY KEY(id));";
        }
    },
    POSTGRESQL {
        @Override
        public String getDatabaseDriver() {
            return "org.postgresql.Driver";
        }

        @Override
        public String getTableCreationQuery() {
            return "CREATE TABLE sev2token (id SERIAL, userId VARCHAR(255), " +
                    "username VARCHAR(255), sev2TokenType VARCHAR (255), uuid VARCHAR (255), PRIMARY KEY(id));";
        }
    };

    public abstract String getDatabaseDriver();

    public abstract String getTableCreationQuery();
}
