DESCRIPTION = "Gnome system monitor"
LICENSE = "GPL"
SECTION = "x11/gnome"
DEPENDS = "libgtop gtkmm glibmm libnotify libgnomeui gnome-panel gnome-doc-utils libwnck gtk+ gnome-keyring libglade hal dbus-glib " 

inherit gnome

do_configure_prepend() {
        sed -i -e s:help::g ${S}/Makefile.am
}

PACKAGES =+ "${PN}-applets"

FILES_${PN}-applets = "${bindir}/*applet* \
                       ${libdir}/bonobo/servers \
		       ${datadir}/gnome-2.0/ui"

FILES_${PN} += "${datadir}/icons \
                ${datadir}/dbus-1 \
		${datadir}/gnome/autostart \
		"

FILES_${PN}-doc += "${datadir}/omf \
                    ${datadir}/gnome/help "


SRC_URI[archive.md5sum] = "341c8fda9b518729e04465705da62994"
SRC_URI[archive.sha256sum] = "638875c6041cd2b0ad7219fecd8fe86eb9d4c8b0662166a84d7659bd6a9db1d3"
