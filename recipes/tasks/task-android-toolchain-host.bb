require task-sdk-host.bb
DESCRIPTION = "Host packages for Android SDK"

# An end user would also want these other things, but they are easy
# enough to install on the host manually:
#RDEPENDS_${PN} += "ant-native virtual/javac-native virtual/java-native"

RDEPENDS_${PN} += "\
    android-tools-sdk \
    androidsdk-sdk \
    "
