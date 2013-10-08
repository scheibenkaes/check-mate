
deploy:
	lein cljsbuild once
	lein ring uberwar
	scp target/checkmate*standalone.war root@scheibenkaes.org:/usr/share/jetty/webapps/checkmate.war
	ssh root@scheibenkaes.org "/etc/init.d/jetty stop"
	ssh root@scheibenkaes.org "/etc/init.d/jetty start"
