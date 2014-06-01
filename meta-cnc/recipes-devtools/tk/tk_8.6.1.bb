SUMMARY = "Tool Command Language"
HOMEPAGE = "http://tcl.sourceforge.net"
SECTION = "devel/tcltk"

# http://www.tcl.tk/software/tcltk/license.html
LICENSE = "tcl & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://license.terms;md5=c88f99decec11afa967ad33d314f87fe"

#DEPENDS = "tcl libx11 zlib "
DEPENDS = "tcl libx11 "

SRC_URI="http://prdownloads.sourceforge.net/tcl/${BPN}${PV}-src.tar.gz"

SRC_URI[md5sum] = "63f21c3a0e0cefbd854b4eb29b129ac6"
SRC_URI[sha256sum] = "b691a2e84907392918665fe03a0deb913663a026bed2162185b4a9a14898162c"


S = "${WORKDIR}/${BPN}${PV}/unix"

VER = "${PV}"

inherit autotools ptest binconfig

#DEPENDS_class-native = "zlib-native"

EXTRA_OECONF = "--with-tcl=${STAGING_DIR_NATIVE}${libdir} --enable-threads --disable-rpath --libdir=${libdir}"

do_configure() {
	cd ${S}
	gnu-configize
	cd ${B}
	oe_runconf
}

do_compile_prepend() {
	echo > ${S}/../compat/fixstrtod.c
}

do_install() {
	autotools_do_install install-private-headers
	ln -sf ./tksh${VER} ${D}${bindir}/tksh
	ln -sf tksh8.6 ${D}${bindir}/tksh${VER}
	sed -i "s+-L${B}+-L${STAGING_LIBDIR}+g" tkConfig.sh
	sed -i "s+${WORKDIR}+${STAGING_INCDIR}+g" tkConfig.sh
	sed -i "s,-L${libdir},-L=${libdir},g" tkConfig.sh
	sed -i "s,-I${includedir},-I=${includedir},g" tkConfig.sh
	install -d ${D}${bindir_crossscripts}
	install -m 0755 tkConfig.sh ${D}${bindir_crossscripts}
	install -m 0755 tkConfig.sh ${D}${libdir}
	cd ..
	for dir in compat generic unix; do
		install -d ${D}${includedir}/${BPN}${VER}/$dir
		install -m 0644 ${S}/../$dir/*.h ${D}${includedir}/${BPN}${VER}/$dir/
	done
}

SYSROOT_PREPROCESS_FUNCS += "tk_sysroot_preprocess"
tk_sysroot_preprocess () {
	sysroot_stage_dir ${D}${bindir_crossscripts} ${SYSROOT_DESTDIR}${bindir_crossscripts}
}

PACKAGES =+ "tk-lib"
FILES_tk-lib = "${libdir}/libtk8.6.so.*"
FILES_${PN} += "${libdir}/tk${VER} ${libdir}/tk8.6 ${libdir}/tk8"
FILES_${PN}-dev += "${libdir}/tkConfig.sh "

# isn't getting picked up by shlibs code
RDEPENDS_${PN} += "tk-lib"
RDEPENDS_${PN}_class-native = ""

BBCLASSEXTEND = "native"

do_compile_ptest() {
    oe_runmake tktest
}

do_install_ptest() {
	cp ${B}/tktest ${D}${PTEST_PATH}
	cp -r ${S}/../library ${D}${PTEST_PATH}
	cp -r ${S}/../tests ${D}${PTEST_PATH}
}

# Fix some paths that might be used by Tcl extensions
BINCONFIG_GLOB = "*Config.sh"

# Fix the path in sstate
SSTATE_SCAN_FILES += "*Config.sh"
