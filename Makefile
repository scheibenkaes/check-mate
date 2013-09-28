
deploy:
	lein cljsbuild once
	lein ring uberwar
	scp target/checkmate*standalone.war root@146.185.148.123:/usr/share/jetty8/webapps/root.war
	ssh root@146.185.148.123 "/etc/init.d/jetty8 restart"
