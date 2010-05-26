DESCRIPTION = "Bacula filedaemon is the client to backup your device using Bacula a network based backup program"
SECTION = "console/network"
PRIORITY = "optional"
HOMEPAGE = "http://www.bacula.org"
LICENSE = "GPL"
PR ="r1"
DEPENDS = "gmp openssl zlib readline"

SRC_URI = "http://heanet.dl.sourceforge.net/sourceforge/bacula/bacula-1.38.11.tar.gz \
           file://findlib-attribs.patch \
           file://bacula-fd.init"

S = "${WORKDIR}/bacula-1.38.11"

# Makefile is broken using ccache
# No idea how to fix it so I turned ccache off
CCACHE = ""

inherit autotools update-rc.d

LDFLAGS += "-L${STAGING_LIBDIR}"
CFLAGS += "-L${STAGING_LIBDIR} -I${STAGING_INCDIR}"

INITSCRIPT_NAME = "bacula-fd"

do_configure() {
        oe_runconf --enable-client-only \
                   --prefix=/usr \
                   --with-working-dir=/var/lib/bacula  \
                   --with-fd-user=root \
                   --with-fd-group=bacula
}

do_compile() {
        oe_runmake CFLAGS="${CFLAGS}" LDFLAGS="${LDFLAGS}"
}

do_install() {
         mkdir -p ${D}/${sbindir}
         install -m 0754 ${S}/src/filed/bacula-fd ${D}/${sbindir}/

         mkdir -p ${D}/${sysconfdir}/bacula
         install ${S}/src/filed/bacula-fd.conf ${D}/${sysconfdir}/bacula/
}

# Add group
pkg_postinst() {
        grep -q bacula: /etc/group || addgroup bacula
}

do_install_append() {
         mkdir -p ${D}/${sysconfdir}/init.d
         install ${WORKDIR}/bacula-fd.init ${D}/${sysconfdir}/init.d/bacula-fd
}

SRC_URI[md5sum] = "0d6d6614afdc468d214de4e66f9f5a26"
SRC_URI[sha256sum] = "d095165d256771c918b80f836feaf837aedc1362b2d67171013d3ab7ac5ee47f"
