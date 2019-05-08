#### OM 调优

#### 1. 优化前的情况
参数设置：java -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/var/log/mps/oamcenter -Xms1g -Xmx5g -Dspring.profiles.active=prod -classpath /opt/mps/oamcenter/libs/jars/*:/opt/mps/java/common/libs/jars/*:/opt/mps/oamcenter/bin/OAMCenter-latest.jar: com.ericsson.web.MpsOAMCenterApplication
(1) Heap
(2) vm

#### 2. 存在问题

#### 3. 举措

#### 4. 优化后的情况