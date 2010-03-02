DESCRIPTION = "Android SDK"
SECTION = "libs"

DEPENDS = "sed-native"
RDEPENDS = "android-tools-native virtual/java-native"
inherit native

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
	install -d ${STAGING_BINDIR_NATIVE}
	for p in ${bins}; do
		cp -pf "${S}"/tools/$p ${STAGING_BINDIR_NATIVE}
	done
	for p in ${java}; do
		sed -e 's,^libdir=,libdir=${STAGING_DIR}/java,' "${S}"/tools/$p >${STAGING_BINDIR_NATIVE}/$p
		chmod +x ${STAGING_BINDIR_NATIVE}/$p
	done

	install -d ${STAGING_DATADIR_NATIVE}
	cp -R -pf "${S}"/android.jar ${STAGING_DATADIR_NATIVE}

	cp -R -pf "${S}"/skins ${STAGING_DATADIR_NATIVE}/emulator
	cp -R -pf "${S}"/images ${STAGING_DATADIR_NATIVE}/emulator

	install -d ${STAGING_DIR_JAVA}
	cp -pf "${S}"/tools/lib/dx.jar ${STAGING_DIR_JAVA}
}
