DESCRIPTION = "Sugar base system"
LICENSE = "LGPLv2"

DEPENDS = "python-pygtk sugar-toolkit"
RDEPENDS_${PN} = "librsvg-gtk \
            gconf \
            matchbox-wm \
            ohm-plugin-x11 ohm \
            hippo-canvas \
            python-datetime \
            python-netclient \
            python-pygtk \
            sugar-toolkit \
            python-logging \
            python-dbus \
            python-subprocess \
            telepathy-gabble telepathy-salut telepathy-python \
            sugar-presence-service \
            python-pycairo \
            python-pygobject \
            python-crypt \
            python-numpy \
            python-compression \
            python-gst \
            python-simplejson \
            python-misc \
            python-xmlrpc \
            python-compiler \
            python-pydoc \
            python-mmap \
            python-doctest \
            openssh-keygen"

PR = "r1"

SRC_URI = "http://download.sugarlabs.org/sources/sucrose/glucose/sugar-base/${PN}-${PV}.tar.bz2"

inherit autotools distutils-base

SRC_URI += "file://acinclude.m4"

EXTRA_OECONF += "--with-python-includes=${STAGING_INCDIR}/../"

do_configure_prepend() {
        install -m 0644 ${WORKDIR}/acinclude.m4 ${S}/
}

FILES_${PN} += "${datadir}/${PN} \
                ${datadir}/dbus-1 \
                ${sysconfdir} "

FILES_${PN}-dbg += "${PYTHON_SITEPACKAGES_DIR}/sugar/.debug"

AUTOTOOLS_STAGE_PKGCONFIG = "1"

SRC_URI[md5sum] = "0ef47ff5a8931e0e7c732d22a514d297"
SRC_URI[sha256sum] = "e67eab2d058a3410d8fe82a2161d0c268b6445648c5901a8bd2c26690c45addd"
