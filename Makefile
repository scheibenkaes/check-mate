
deploy:
	lein cljsbuild once
	lein ring uberwar
	scp target/checkmate*standalone.war root@scheibenkaes.org:/usr/share/jetty/webapps/root.war
	ssh root@scheibenkaes.org "/etc/init.d/jetty stop"
	ssh root@scheibenkaes.org "CHECKMATE_REPL=1 /etc/init.d/jetty start"
