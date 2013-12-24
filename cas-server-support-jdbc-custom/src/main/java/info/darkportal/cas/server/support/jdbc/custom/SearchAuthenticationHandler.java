package info.darkportal.cas.server.support.jdbc.custom;

import org.jasig.cas.adaptors.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.springframework.beans.factory.InitializingBean;

import javax.validation.constraints.NotNull;

public class SearchAuthenticationHandler extends
    AbstractJdbcUsernamePasswordAuthenticationHandler implements InitializingBean {

    private static final String SQL_PREFIX = "Select ";

    @NotNull
    private String fieldUser;

    @NotNull
    private String fieldSalt;
    
    @NotNull
    private String fieldPassword;

    @NotNull
    private String tableUsers;

    private String sql;

    protected final boolean authenticateUsernamePasswordInternal(final UsernamePasswordCredentials credentials) throws AuthenticationException {
        final String transformedUsername = getPrincipalNameTransformer().transform(credentials.getUsername());
        final User user = getJdbcTemplate().queryForObject(sql, new UserRowMapper(), new Object[] {transformedUsername});
        if (user != null){
        	//TODO: this should be responsible by the encoder but cas 3.5 encoder support only 1 params
        	String pass = user.getSalt() + "::" + credentials.getPassword();
        	final String encyptedPassword = getPasswordEncoder().encode(pass);
        	return encyptedPassword.equals(user.getEncryptedPassword());
        }
        return false;
    }

    public void afterPropertiesSet() throws Exception {
        this.sql = SQL_PREFIX + this.fieldUser + ", " + this.fieldSalt + ", " + this.fieldPassword 
        		+ " from " + this.tableUsers
        		+ " Where " + this.fieldUser + " = ?"; 
    }

    /**
     * @param fieldPassword The fieldPassword to set.
     */
    public final void setFieldPassword(final String fieldPassword) {
        this.fieldPassword = fieldPassword;
    }

    /**
     * @param fieldUser The fieldUser to set.
     */
    public final void setFieldUser(final String fieldUser) {
        this.fieldUser = fieldUser;
    }
    
    /**
     * @param fieldUser The fieldSalt to set.
     */
    public final void setFieldSalt(final String fieldSalt) {
        this.fieldSalt = fieldSalt;
    }

    /**
     * @param tableUsers The tableUsers to set.
     */
    public final void setTableUsers(final String tableUsers) {
        this.tableUsers = tableUsers;
    }
}