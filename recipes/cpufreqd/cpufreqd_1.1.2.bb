DESCRIPTION = "cpufreqd is meant to be a replacement of the speedstep \
applet you can find in Windows. It monitors battery level, CPU usage, \
AC state, and running programs and adjusts the frequency governor \
according to a set of rules specified in the config file."
SECTION = "console/utils"
LICENSE = "GPL"

SRC_URI = "${SOURCEFORGE_MIRROR}/cpufreqd/cpufreqd-${PV}.tar.gz \
	   file://nonrootinstall.patch"

inherit autotools

FILES_${PN} = "${sbindir} ${sysconfdir} ${libdir}/libsys_*.so"
FILES_${PN}-dev = "${libdir}/*.la ${libdir}/*.a"

SRC_URI[md5sum] = "d06dcf3e3b2f8eb25216431ec055aa4b"
SRC_URI[sha256sum] = "c64eb1923366934357c917ff9a94237a8157de24a1fd96e0f91c4d5dfad83026"
