DESCRIPTION = "GPS application for openmoko freerunner"
HOMEPAGE = "http://omgps.googlecode.com"
SECTION = "openmoko/applications"
LICENSE = "GPLv2"
DEPENDS = "gtk+ python-pygobject dbus-glib"
#PACKAGES = "${PN}-dbg ${PN}"
SRCREV = "109"
PV = "0.1+svnr${SRCPV}"
PR = "r1"
S = "${WORKDIR}/${PN}"
SRC_URI = "svn://omgps.googlecode.com/svn/trunk;module=omgps;proto=http \
           file://gcc-4.4.patch \
	   file://sysfs.node.2.6.32.patch"

inherit autotools
