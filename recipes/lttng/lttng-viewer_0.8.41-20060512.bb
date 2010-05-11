SECTION = "devel"
DESCRIPTION = "The Linux trace toolkit is a suite of tools designed to \
extract program execution details from the Linux operating system and  \
interpret them."
LICENSE = "GPL"
PR = "r1"
DEPENDS = "gtk+ pango popt"

ALTNAME = "LinuxTraceToolkitViewer-0.8.41-12052006"

SRC_URI = "http://ltt.polymtl.ca/packages/${ALTNAME}.tar.gz "
S = "${WORKDIR}/${ALTNAME}"

inherit autotools

FILES_${PN} += "\
    ${libdir}/lttv/plugins/* \
    ${datadir}/LinuxTraceToolkitViewer/facilities/* \
    ${datadir}/LinuxTraceToolkitViewer/pixmaps/* "

SRC_URI[md5sum] = "de139052f234cf54d714c960524bd5ef"
SRC_URI[sha256sum] = "a21431358d7f2a5deb76b1a95ee80dc870b0dc2426d4860c61144343d48eff3e"
