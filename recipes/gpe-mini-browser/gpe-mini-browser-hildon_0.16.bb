require gpe-mini-browser.inc

PR = "r0"

SRC_URI      = "${GPE_MIRROR}/gpe-mini-browser-${PV}.tar.gz"
DESCRIPTION  = "A lightweight webbrowser for the GPE platform (Hildon UI)"
DEPENDS      = "osb-nrcit libosso hildon-lgpl hildon-fm libgpewidget"
EXTRA_OECONF = "--enable-hildon"

S = "${WORKDIR}/gpe-mini-browser-${PV}"

inherit autotools

do_install() {
		install -d ${D}/usr/share/applications/hildon
		install -m 0644 ${S}/hildon/gpe-mini-browser.desktop ${D}/usr/share/applications/hildon/gpe-mini-browser.desktop
		install -d ${D}/usr/share/pixmaps
		install -m 0644 ${S}/gpe-mini-browser.png ${D}/usr/share/pixmaps/gpe-mini-browser.png
		autotools_do_install
}

pkg_postinst_${PN}-doc () {
        #!/bin/sh
	if [ "x$D" != "x" ]; then
        if [ -e /etc/gpe/gpe-help.conf ]; then
                echo gpe-mini-browser= /usr/share/doc/gpe/gpe-mini-browser.html >> /etc/gpe/gpe-help.conf
        else
                 echo [Help] >> /etc/gpe/gpe-help.conf
                 echo gpe-mini-browser= /usr/share/doc/gpe/gpe-mini-browser.html >> /etc/gpe/gpe-help.conf
        fi
        if [ -x /usr/bin/gpe-helpindex ]; then
                echo generating help-index
                gpe-helpindex
        else
                echo not generating index for gpe-mini-browser
        fi
	fi
}

pkg_postrm_${PN}-doc () {
        #!/bin/sh
        if [ -e /etc/gpe/gpe-help.conf ]; then
                sed '/^\<gpe-mini-browser\>/d' /etc/gpe/gpe-help.conf > /tmp/gpe-help.conf
                mv /tmp/gpe-help.conf /etc/gpe/gpe-help.conf
        fi
        if [ -x /usr/bin/gpe-helpindex ]; then
                echo generating help-index
                gpe-helpindex
        else
                echo not generating index for gpe-mini-browser
        fi
}

SRC_URI[md5sum] = "858f3e7219cde7b18e69293526020416"
SRC_URI[sha256sum] = "36d2487895d3579ae3e8693ebe789b5de454506fc572937dac2fb7ef512f105f"
