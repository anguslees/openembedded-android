require gpe-mini-browser.inc

PR = "r1"

SRC_URI = "ftp://ftp.handhelds.org/projects/gpe/source/gpe-mini-browser-${PV}.tar.gz"
DEPENDS = "osb-nrcit libgpewidget"

S = "${WORKDIR}/gpe-mini-browser-${PV}"

inherit autotools

do_install() {
	#	install -d ${D}${docdir}/gpe
	#	install -m 0644 ${S}/gpe-mini-browser.html  ${D}${docdir}/gpe/
		install -d ${D}/usr/share/applications
		install -m 0644 ${S}/gpe-mini-browser.desktop ${D}/usr/share/applications/gpe-mini-browser.desktop
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

SRC_URI[md5sum] = "b048cee7254d7fe6eb1e2821ce8706df"
SRC_URI[sha256sum] = "aa9c2dbe8d763b4461a42d46378cc49335a266a7d6dcd794ecd46fac037c8913"
