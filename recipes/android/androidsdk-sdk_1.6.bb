DESCRIPTION = "Android SDK"
SECTION = "libs"

DEPENDS += "sed-native"
RDEPENDS += "android-tools-sdk"
# Make end user provide this themselves
# RDEPENDS += virtual/java-native
inherit java
inherit sdk

INHIBIT_DEFAULT_DEPS = "1"

PV = 1.6_r02
SRC_URI = "http://dl.google.com/android/repository/android-${PV}-${BUILD_OS}.zip"

S = "${WORKDIR}/android-${PV}-${BUILD_OS}"

do_compile() {
	:
}

bins = "dexdump aapt aidl"
java = "dx"

do_stage() {
	install -d ${STAGING_BINDIR}
	for p in ${bins}; do
		cp -pf "${S}"/tools/$p ${STAGING_BINDIR}
	done

	for p in ${java}; do
		sed -e 's,^libdir=.*,libdir=${STAGING_DIR_JAVA},' "${S}"/tools/$p >${STAGING_BINDIR}/$p
		chmod +x ${STAGING_BINDIR}/$p
	done

	install -d ${STAGING_DATADIR}
	cp -R -pf "${S}"/skins ${STAGING_DATADIR}/emulator
	cp -R -pf "${S}"/images ${STAGING_DATADIR}/emulator

	oe_jarinstall -s ${S}/android.jar
	oe_jarinstall -s ${S}/tools/lib/dx.jar
}

do_install() {
	install -d ${D}${bindir}
	for p in ${bins}; do
		install -m 0755 "${S}"/tools/$p ${D}${bindir}
	done

	for p in ${java}; do
		sed -e 's,^libdir=.*,libdir=${datadir}/java,' "${S}"/tools/$p >${D}${bindir}/$p
		chmod +x ${D}${bindir}/$p
	done

	oe_jarinstall ${S}/android.jar
	oe_jarinstall ${S}/tools/lib/dx.jar	
}
