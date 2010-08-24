SECTION = "console/utils"
LICENSE = "GPL"
DESCRIPTION = "A tool to encode and decode the Sharp Zaurus updater.sh script"

SRC_URI = "file://encdec-updater.c"

COMPATIBLE_MACHINE = '(poodle|c7x0|spitz|akita|tosa)'

BBCLASSEXTEND = "native"
NATIVE_INSTALL_WORKS = "1"

do_compile() {
	${CC} ${LDFLAGS} -o encdec-updater ${WORKDIR}/encdec-updater.c
}

do_install() {
	install -d ${D}${bindir}
	install -m 0755 encdec-updater ${D}${bindir}/
}
