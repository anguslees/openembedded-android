require networkmanager-0.7.inc

DEFAULT_PREFERENCE = "-1"

PR = "r4"

SRC_URI += " \
    file://remove-gtk-doc-make.patch \
    file://nm-system-settings.conf \
    file://NetworkManager \
    file://gtk-doc.make \
"


do_configure_prepend() {
    cp ${WORKDIR}/gtk-doc.make ${S}/
    echo "EXTRA_DIST = version.xml" > gnome-doc-utils.make
    sed -i -e 's:man \\:man:' -e s:docs::g ${S}/Makefile.am
    sed -i -e /^docs/d ${S}/configure.ac
}

FILES_${PN} += "  ${datadir}/polkit-1/"

S = "${WORKDIR}/NetworkManager-${PV}"

do_install_append () {
	install -d ${D}/etc/NetworkManager/
	install -m 0644 ${WORKDIR}/nm-system-settings.conf ${D}/etc/NetworkManager/
	install -m 0755 ${WORKDIR}/NetworkManager ${D}/etc/init.d
	
	# Install an empty VPN folder as nm-connection-editor will happily segfault without it :o.
	# With or without VPN support built in ;).
	install -d ${D}/etc/NetworkManager/VPN
}


SRC_URI[md5sum] = "d343e22f132754696654511acde354c2"
SRC_URI[sha256sum] = "4ac38f5c6ddbbcc67601ba898dd475457e2d259b77f9680eee51cb07f19c1b05"
