import styles from './Element.module.scss'

type ElementViewProps = ElementProps
function ElementView({
  data,
  handleDelete,
  dateId,
  handleEditBtnClick,
  isMember,
}: ElementViewProps) {
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
      {isMember && (
        <div className={styles.buttons}>
          <button
            type='button'
            className={styles.okBtn}
            onClick={() => {
              handleEditBtnClick(dateId, data.itemId)
            }}
          >
            수정
          </button>
          <button
            type='button'
            className={styles.deleteBtn}
            onClick={() => handleDelete(dateId, data.itemId)}
          >
            삭제
          </button>
        </div>
      )}
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
  dateId: number
  isMember: boolean
  handleEditBtnClick: (dateId: number, itemId: number) => void
  handleDelete: (dateId: number, itemId: number) => void
}
function Element({ data, handleDelete, dateId, handleEditBtnClick, isMember }: ElementProps) {
  return (
    <ElementView
      data={data}
      handleDelete={handleDelete}
      dateId={dateId}
      handleEditBtnClick={handleEditBtnClick}
      isMember={isMember}
    />
  )
}

export default Element
