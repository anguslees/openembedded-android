DESCRIPTION = "Sugar base system"
LICENSE = "LGPLv2"

PR = "r12"

DEPENDS = "python-pygtk sugar-toolkit"
RDEPENDS_${PN} = "librsvg-gtk \
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
	    ssh-keygen"

SRC_URI = "http://dev.laptop.org/pub/sugar/sources/sugar-base/${PN}-${PV}.tar.bz2"

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

SRC_URI[md5sum] = "c5790a18fd9ee86a49ffa727505294f5"
SRC_URI[sha256sum] = "430b27a0510a50fb66ee211e35565be7e1e522bcde7d1adde5c63a1d18ad7f1d"
