# Zeppelin HowTo

## Install Zeppelin
1. Run the script ${HOME}/shmack/repo/04_implementation/scripts/open-shmack-master-console.sh
2. Install Zeppelin by clicking: Universe -> Search for 'Zeppelin', install
3. After installation configure Zeppelin to be accessible from outside the cluster by:
4. Go to Zeppelin in the Marathon UI (by running ${HOME}/shmack/repo/04_implementation/scripts/open-shmack-marathon-ui.sh), then Configuration, Edit, Labels
5. Add the label HAPROXY_GROUP with value external
6. Add the label HAPROXY_0_PORT with the value 8081
7. Restart Zeppelin
8. Open Zeppelin with ${HOME}/shmack/repo/04_implementation/scripts/open-shmack-zeppelin.sh

## Connect Zeppelin to Cassandra
1. In the SHMACK VM type dcos cassandra connection
2. Insert one of the dns names (without the port) in the cassandra interpreter config in Zeppelin
