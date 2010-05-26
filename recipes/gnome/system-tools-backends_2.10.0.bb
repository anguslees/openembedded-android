DESCRIPTION = "gnome system tools backends"
LICENSE = "GPL"

DEPENDS = "dbus dbus-glib glib-2.0 policykit"

# Shadow added so there is a full adduser/deluser
# (Gnome images tend to pull in shadow anyway)
RDEPENDS = "shadow"

inherit gnome pkgconfig update-rc.d

SRC_URI += " \
            file://system-tools-backends \
           "

SRC_URI_append_angstrom = " \
            file://add-angstrom-distro.patch \
           "

SRC_URI[archive.md5sum] = "01fd8fcfbe5f68492186404c96af915a"
SRC_URI[archive.sha256sum] = "5afea48c0c195dd3b6e0b87a586c08bf614942d4bcb60449fd9f65d95418b60d"

EXTRA_OECONF = " --with-net-dbus=${libdir}/perl5 "

do_configure() {
	sed -i -e 's:CC=$(CC):CC="$(CC)":g' ${S}/Net-DBus/Makefile.am
	sed -i -e 's:CC=$(CC):CC="$(CC)":g' ${S}/Net-DBus/Makefile.in
	gnu-configize
	oe_runconf
}

do_install_append () {
	install -d ${D}/${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/system-tools-backends ${D}/${sysconfdir}/init.d/	
}

INITSCRIPT_NAME = "system-tools-backends"
INITSCRIPT_PARAMS = "start 50 2 3 4 5 . stop 70 1 ."

FILES_${PN} += " ${sysconfdir}/dbus-1/system.d"
FILES_${PN} += " ${libdir}/pkgconfig"
FILES_${PN} += " ${datadir}/dbus-1/system-services"
FILES_${PN} += " ${datadir}/system-tools-backends-2.0/files"
FILES_${PN} += " ${datadir}/system-tools-backends-2.0/scripts"
FILES_${PN} += " ${datadir}/polkit*"
