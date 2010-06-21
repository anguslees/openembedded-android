DESCRIPTION = "distcc is a parallel build system that distributes \
compilation of C/C++/ObjC code across machines on a network."
SECTION = "devel"
LICENSE = "GPLv2"
PR = "r5"

DEPENDS = "avahi gtk+"
RRECOMMENDS_${PN} = "avahi-daemon"

SRC_URI = "http://distcc.samba.org/ftp/distcc/distcc-${PV}.tar.bz2 \
	   file://distcc-avahi.patch \
	   file://no-man.patch \
           file://default \
	   file://distccmon-gnome.desktop \
	   file://distcc"

inherit autotools pkgconfig update-rc.d

INITSCRIPT_NAME = "distcc"

EXTRA_OECONF = " --with-gtk "

do_install_append() {
    install -d ${D}${sysconfdir}/init.d/
    install -d ${D}${sysconfdir}/default
    install -d ${D}${sysconfdir}/distcc
    install -m 0755 ${WORKDIR}/distcc ${D}${sysconfdir}/init.d/
    install -m 0755 ${WORKDIR}/default ${D}${sysconfdir}/default/distcc
    install -m 0644 ${WORKDIR}/distccmon-gnome.desktop ${D}${datadir}/distcc/
    echo "+zeroconf" > ${D}${sysconfdir}/distcc/hosts
}

PACKAGES += "distcc-distmon-gnome"

FILES_${PN} = " ${sysconfdir} \
		${bindir}/distcc \
		${bindir}/distccd \
		${bindir}/distccmon-text"

CONFFILES_${PN} += "${sysconfdir}/default/distcc \
                    ${sysconfdir}/distcc/hosts \
                    ${sysconfdir}/init.d/distcc \
                   " 

FILES_distcc-distmon-gnome = "  ${bindir}/distccmon-gnome \
				${datadir}/distcc"

SRC_URI[md5sum] = "0d6b80a1efc3a3d816c4f4175f63eaa2"
SRC_URI[sha256sum] = "6500f1bc2a30b1f044ebed79c6ce15457d1712263e65f0db7d6046af262ba434"
