DESCRIPTION = "PackageKit package management abstraction"
SECTION = "libs"
PRIORITY = "optional"
LICENSE = "GPL"
DEPENDS = "gtk+ python cppunit policykit dbus (>= 1.1.1) dbus-glib glib-2.0 sqlite3 ${IPKG_VARIANT} intltool intltool-native (>= 0.37.1)"
RDEPENDS_${PN} = "${IPKG_VARIANT}"

inherit gnome autotools_stage

SRC_URI = "http://www.packagekit.org/releases/PackageKit-${PV}.tar.gz \
           file://no_validate.patch \
          "

PR = "r0"
PE = "1"

S = "${WORKDIR}/PackageKit-${PV}"

EXTRA_OECONF = "--with-security-framework=dummy \
                --with-default-backend=opkg \
                --enable-opkg \
                --disable-tests \
                --disable-qt \
                --disable-gstreamer-plugin \
                --disable-local  \
                ac_cv_path_XMLTO=no \
                "


do_configure_prepend() {
	mkdir -p m4
	echo "EXTRA_DIST=" > gtk-doc.make
	sed -i -e s:0\.1\.5:0\.1\.6:g -e /Werror/d configure.ac
}

do_configure_append() {
	for i in $(find . -name Makefile) ; do
	        sed -i -e s:${STAGING_DIR_NATIVE}::g \
               -e s:${bindir}/mkdir:${STAGING_BINDIR_NATIVE}/mkdir:g \
               -e s:/usr/bin/intltool-merge:${STAGING_BINDIR_NATIVE}/intltool-merge:g \
	$i
	done
}


PACKAGES =+ "${PN}-website"
FILES_${PN}-website = "${datadir}/PackageKit/website"

PACKAGES =+ "${PN}-python"
FILES_${PN}-python = "${libdir}/python*"

PACKAGES =+ "${PN}-gtkmodule"
FILES_${PN}-gtkmodule = "${libdir}/gtk-2.0/*/*.so"

FILES_${PN} += "${libdir}/packagekit-backend/*.so ${libdir}/pm-utils ${datadir}/dbus-1/system-services/ ${datadir}/PolicyKit ${datadir}/PackageKit"
FILES_${PN}-dbg += "${libdir}/packagekit-backend/.debug/*.so ${libdir}/gtk-2.0/*/.debug"
FILES_${PN}-dev += "${libdir}/packagekit-backend/*a ${libdir}/gtk-2.0/*/*a"




SRC_URI[md5sum] = "955082ee50358b1cc3eddcb438b7fae4"
SRC_URI[sha256sum] = "70e7fb5e8cc3a35a04213230e1e4340ddc8766a2615318086da7d51ec930f6f2"
