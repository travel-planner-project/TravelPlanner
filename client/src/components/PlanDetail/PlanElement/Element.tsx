import styles from './Element.module.scss'

type ElementViewProps = ElementProps
function ElementView({ data }: ElementViewProps) {
  return (
    <div className={styles.container}>
      <div className={styles.itemBox}>
        <div className={styles.header}>
          <div className={styles.title}>{data.itemTitle}</div>
          <div className={styles.category}>{data.category}</div>
        </div>
        <div className={styles.itemInfo}>
          {data.itemTime ? <div className={styles.itemTime}>{data.itemTime}</div> : null}
          {data.budget ? (
            <div className={styles.budgetBox}>
              <div className={styles.itemPayment}>{data.budget}</div>
              <span>원</span>
            </div>
          ) : null}
        </div>
        <div className={styles.itemAddress}>{data.itemAddress}</div>
        <div className={styles.itemDetail}>{data.itemContent}</div>
      </div>
      <div className={styles.buttons}>
        <div className={styles.okBtn}>수정</div>
        <div className={styles.deleteBtn}>삭제</div>
      </div>
    </div>
  )
}
type ElementProps = {
  data: {
    // dateId: number
    itemId: number
    itemTitle: string
    itemTime: string
    category: string
    itemContent: string
    isPrivate: boolean
    budget: number | null
    itemAddress: string
  }
}
function Element({ data }: ElementProps) {
  return <ElementView data={data} />
}

export default Element
