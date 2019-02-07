package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.core.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {

	  @Override
	    protected String getKeyspaceName() {
	        return "cwt";
	    }
	  
	  @Bean
	    public CassandraClusterFactoryBean cluster() {
	        CassandraClusterFactoryBean cluster = 
	          new CassandraClusterFactoryBean();
	        cluster.setContactPoints("192.168.1.36");
	        cluster.setPort(9042);
	        return cluster;
	    }
	 
	    @Bean
	    public CassandraMappingContext cassandraMapping() 
	      throws ClassNotFoundException {
	        return new BasicCassandraMappingContext();
	    }

}
