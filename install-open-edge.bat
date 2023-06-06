REM Installs openedge driver

mvn install:install-file -Dfile=bill-of-materials\lib\openedge.jar -DgroupId=com.ddtek.jdbc.openedge -DartifactId=OpenEdgeDriver -Dversion=10.2 -Dpackaging=jar -DgeneratePom=true
