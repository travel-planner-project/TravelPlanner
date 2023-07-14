import styles from './Element.module.scss'

function ElementView() {
  return (
    <div className={styles.container}>
      <div className={styles.itemBox}>
        <div className={styles.header}>
          <div className={styles.title}>조은호텔 체크인</div>
          <div className={styles.category}>숙박</div>
        </div>
        <div className={styles.itemInfo}>
          <div className={styles.itemTime}>오후 15시 00분</div>
          <div className={styles.itemPayment}>165,000 원</div>
        </div>
        <div className={styles.itemAddress}>제주시 특별자치도, 한립음 협재리 30</div>
        <div className={styles.itemDetail}>물놀이 복장으로 갈아입기 ㅎㅎ</div>
      </div>
      <div className={styles.buttons}>
        <div className={styles.okBtn}>확인</div>
        <div className={styles.deleteBtn}>삭제</div>
      </div>
    </div>
  )
}
function Element() {
  return <ElementView />
}

export default Element
